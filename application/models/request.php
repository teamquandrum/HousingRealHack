<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Request extends ExposedModel {
    /**
     * @Override
     */
    public function getTable() {
        return 'request';
    }
    public function newRequest($params) {
        $params['status'] = 1;
        $req = array('sid','aid','status');
        $this->newEntry($params, $req);
    }
    
    public function getSentRequests($params) {
        $req = array('sid');
        return $this->getEntries($params, $req);   
    }
    public function getReceivedRequests($params) {
        $req = array('aid');
        return $this->getEntries($params, $req);
    }
}
