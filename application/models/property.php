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
    
    public function getCentroid($params) {
        $req = ['lats','lons'];
        $this->throwExceptionOnUnset($params, $req);
        $params['lats'] = explode(":", $params['lats']);
        $params['lons'] = explode(":", $params['lons']);
        $params['lats'][count($params['lats'])]=$params['lats'][0];
        $params['lons'][count($params['lons'])]=$params['lons'][0];
        return $params; 
    }
}