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
        $req = ['lats','lons','budget'];
        $this->throwExceptionOnUnset($params, $req);
        $params['lats'] = explode(":", $params['lats']);
        $params['lons'] = explode(":", $params['lons']);
        $params['lats'][]=$params['lats'][0];
        $params['lons'][]=$params['lons'][0];
        
        // calculate area
        $area = 0.0;
        $x=0.0; $y=0.0;
        for($i=0;$i<count($params['lons'])-1;$i++) {
            $xi = $params['lats'][$i]; $xi1 = $params['lats'][$i+1];
            $yi = $params['lons'][$i]; $yi1 = $params['lons'][$i+1];
            $area+=$xi*$yi1 - $xi1*$yi;
            $x+=($xi+$xi1)*($xi*$yi1 - $xi1*$yi);
            $y+=($yi+$yi1)*($xi*$yi1 - $xi1*$yi);
        }
        $area/=2;
        echo $area;
        if($area==0) {
           echo $x;
           echo $y;
        } else {
            $x = $x/6/$area;
            $y = $y/6/$area;
        }
        return [$x,$y]; 
    }
}