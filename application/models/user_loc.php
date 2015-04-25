<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class User_loc extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'user_loc';
    }
    //creates an independent request
    public function newUserLoc($params) {
        $req = array('userid','lat','lon');
        $existing = $this->getEntries($params, ['userid']);
        //TODO: replace bottom lines with insert on update
        if(count($existing)==0) $this->newEntry($params,$req);
        else $this->updateEntries($params,['lat','lon'],['userid']);
    }
    //new interest for a user
    public function getUserLoc($params) {
        $req = array('userid');
        return $this->getEntries($params, $req);
    }
}
