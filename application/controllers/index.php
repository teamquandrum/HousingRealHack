<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Hello extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -  
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in 
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
		$params = $_REQUEST;
		$this->load->database();
		$this->load->model('User','',TRUE);
		$this->User->newEntry($_REQUEST);
	}
	public function verifyRequest($params)
	{
		$applications=array(
			'APP0001'=>'as12d2gklvjo4p345jjkgjjlokp62h21'
			);
		try{
			//retrieve required data
			$enc=$_REQUEST['enc'];
			$appid=$_REQUEST['appid'];
			
			if(!isset($applications['appid']))
			{
				throw new Exception('Application not registered');
			}
		}
		catch(Exception $e)
		{
			echo "<br>",$e->getMessage();
		}
	}
}

