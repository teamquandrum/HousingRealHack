<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Offer extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'offer';
    }
    //creates a new offer for a place
    public function newOffer($params) {
        $req = ['qid','helperid'];
        //$this->newEntry($params,$req);
        $this->load->model('Question');
        $u_resp = $this->Question->getUserForQuestion($params);
        $params['askerid'] = $u_resp[0]['userid'];
        $this->load->model('Gcm');
        $this->Gcm->sendOfferMessage($params);
    }
    //gets offer for a place
    public function getOffersForQuestion($params) {
        $req = ['qid'];
        return $this->getEntries($params, $req);
    }
}
