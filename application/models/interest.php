<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Interest extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'interest';
    }
    //creates an independent request
    public function newInterest($params) {
        $req = array('iname');
        $this->newEntry($params,$req);
    }
    //new interest for a user
    public function getInterests($params) {
        $req = array();
        return $this->getEntries($params, $req);
    }
}
