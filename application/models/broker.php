<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Broker extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'broker';
    }
    //creates a new request
    public function newBroker($params) {
        $req = ['brokerid','name'];
        $this->newEntry($params,$req);
    }
    
    public function getPoints($params) {
        $req = ['brokerid'];
        $query = "Select count(*)as points from propvideo where brokerid={$params['brokerid']}";
        return $this->db->query($query)->result_array()[0];
    }
}
