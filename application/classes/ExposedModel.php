<?php
abstract class ExposedModel extends CI_Model {
    
    function __construct() {
        parent::__construct();
        $this->load->database();
    }
     /** Returns name of table */
    abstract function getTable();
    
    /**
     * returns associative array of only required paramaters
     * expects all required parameters to be present
     * @param associative array $params
     */
    public function filterParams($params, $req) {
        $filteredParams = array(); // array() necessary for compatibility issues? Compilation error sometimes? (000webhost, I believe)
        foreach($req as $key) {
            $filteredParams[$key] = $params[$key];
        }
        return $filteredParams;
    }
    
    public function getUnsetParams($params, $req) {
        $unset = array();
        foreach($req as $key) {
            if(!isset($params[$key])) {
                $unset[] = $key;
            }
        }
        return $unset;
    }
    public function throwExceptionOnUnset($params, $req) {
        $unset = $this->getUnsetParams($params, $req);
            if(count($unset)!=0) {
                throw new Exception("Failed to receive ".json_encode($unset));
            }
    }
    /**
     * Expects array params which can be directly insert into table
     * @param associative array $params 
     */
    public function newEntry($params, $req) {
        $this->throwExceptionOnUnset($params, $req);        
        $filtered = $this->filterParams($params, $req);
        $response = $this->db->insert($this->getTable(),$filtered);
        return $this->db->insert_id();
    }
    /**
     * convenience function to get entries based on a specific parameter     * 
     * @param type $params
     * @param type $req where cllause and filter
     * @return type
     */
    public function getEntries($params, $req) {
        $this->throwExceptionOnUnset($params, $req);
        $filtered = $this->filterParams($params, $req);
        $response = $this->db->get_where($this->getTable(),$filtered)->result_array();
        return $response;
    }
    /**
     * uses $setReq and $whereReq to determine what to update and what to search for
     * @param type $params
     * @param type $req
     * @return type
     */
    public function updateEntries($params, $setReq, $whereRec) {
        $this->throwExceptionOnUnset($params, $setReq);
        $this->throwExceptionOnUnset($params, $whereRec);
        $set = $this->filterParams($params, $setReq);
        $where = $this->filterParams($params, $whereRec);
        $response = $this->db->update($this->getTable(), $set, $where);
        return $response;
    }
}
?>

