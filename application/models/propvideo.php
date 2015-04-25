<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Propvideo extends ExposedModel {
    /**
     * @Override
     */
    public function getTable() {
        return 'propvideo';
    }
    public function newPropVideo($params) {
        $req = array('propid','url','brokerid');
        $this->newEntry($params, $req);
    }
    
    public function getPropVideo($params) {
        $req = array('propid');
        return $this->getEntries($params, $req);   
    }
}
