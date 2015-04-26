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
        $req = array("name","gender", "openness","conscience","extroverted","agreeable","neurotic","budget");
        
        $result['userid'] = $this->newEntry ($params, $req);
        return $result;
    }
    public function getRoommates($params) {
        $req = ['userid','budget','lat','lon'];
        $user = $this->getEntries($params, ['userid']);
        $budget = $params['budget'];
        // first look for people with +-2000 rupees budget
        $query = "Select *, -from user where budget > $budget-2000 AND budget < $budget + 2000";
        return $this->db->query($query)->result_array();
    }
}
