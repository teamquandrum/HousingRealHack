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
        $req = array("name","model","gcmregid");
        
        $existingUsers = $this->getEntries ($params, array('gcmregid'));
        if(count($existingUsers)!=0) return $existingUsers[0];
        
        $result['userid'] = $this->newEntry ($params, $req);
        return $result;
    }
    public function getUser($params) {
        $req = array('userid');       
        return $this->getEntries($params, $req);
    }
}
