<?php
require_once(APPPATH . 'classes/ExposedModel.php');
class Property extends ExposedModel {
    
    //@Override
    public function getTable() {
        return 'property';
    }
    //creates a new offer for a place
    public function newProperty($params) {
        if(!isset($params['rentable'])) $params['rentable'] = 0;
        $req = ['ownerid','price','bhk','lat','lon','numppl','rentable','description','address'];
        return $this->newEntry($params,$req);
    }
    
    public function getProperty($params) {
        $req = ['userid'];
        // draw polygon, get centroid
        return $this->getEntries($params,[]);
    }
    
    private function getCentroid($params) {
        $req = ['lats','lons'];
        $this->throwExceptionOnUnset($params, $req);
        $params['lats'][count(lats)]=$params['lats'][0];
        $params['lons'][count(lons)]=$params['lons'][0];
        return $params;
    }
}
