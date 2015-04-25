<?php
// increase execute time
// make a recursive function
// stop if file exists
//this recursive call (pulsing) will stop when below mentioned files is created
class Autopulser extends CI_Controller
{
    function startPulsing() {
        ini_set('max_execution_time', 0);
        $this->recursivePulse();
    }
    
    function recursivePulse() {        
        $this->load->model('Pulse');
        $this->Pulse->routine_activities();
        echo "test";
        $SENTINEL_FILE = "stoppulsing";
        if(!file_exists($SENTINEL_FILE)) {
            sleep(1);
            $this->recursivePulse();
        }
    }
}
?>