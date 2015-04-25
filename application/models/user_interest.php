<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class User_interest extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'user_interest';
    }
    //creates an independent request
    public function newUserInterest($params) {
        $req = array('userid','iname');
        $this->newEntry($params,$req);
    }
    //new interest for a user
    public function getUserInterest($params) {
        $req = array('userid');
        return $this->getEntries($params, $req);
    }
}
