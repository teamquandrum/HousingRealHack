<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class User extends ExposedModel {
    /**
     * @Override
     */
    public function getTable() {
        return 'user';
    }
    public function newUser($params) {
        $params['budget'] = 2000;
        if(!isset($params['phno'])) $params['phno']=  "923".rand (0, 9).rand (0, 9).rand (0, 9).rand (0, 9).rand (0, 9).rand (0, 9).rand (0, 9);
        $req = array("name","gender", "openness","conscience","extroverted","agreeable","neurotic","budget","phno");
        
        $result['userid'] = $this->newEntry ($params, $req);
        return $result;
    }
    public function getRoommates($params) {
        $req = ['userid','budget','lat','lon'];
        $user = $this->getEntries($params, ['userid'])[0];
        $budget = $params['budget'];
        // first look for people with +-2000 rupees budget
        $query = "Select *, "
                . "POW({$user['openness']}-openness,2)+"
                . "POW({$user['conscience']}-conscience,2)+"
                . "POW({$user['neurotic']}-neurotic,2)+"
                . "POW({$user['agreeable']}-agreeable,2)+"
                . "POW({$user['extroverted']}-extroverted,2)"
                . "as score "
                . "from user where budget > $budget-2000 AND budget < $budget + 2000"
                        . " order by score";
        return $this->db->query($query)->result_array();
    }
}
