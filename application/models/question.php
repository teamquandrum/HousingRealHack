<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Question extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'question';
    }
    //creates a new request
    public function newQuestion($params) {
        $params['importance']=0;
        $req = ['title','content','askerid'];
        $this->newEntry($params,$req);
    }
    //all questions in db
    public function getAllQuestions($params) {
        $req = [];
        return $this->getEntries($params, $req);
    }
    //all questions for a user
    public function getAllQuestionsFor($params) {
        $req = ['askerid'];
        return $this->getEntries($params, $req);
    }
    
    public function getUserForQuestion($params) {
        $req = ['qid'];
        $this->load->model('User');
        $q_resp = $this->getEntries($params, $req);
        
        return $this->User->getUser(['userid'=>$q_resp[0]['askerid']],['userid']);
       
    }
}
