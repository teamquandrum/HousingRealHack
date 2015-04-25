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
        $req = array("name","gender", "openness","conscience","extroverted","agreeable","neurotic");
        
        $result['userid'] = $this->newEntry ($params, $req);
        return $result;
    }
    public function getUser($params) {
        $req = array('userid');       
        return $this->getEntries($params, $req);
    }
}
