<?php
class Manager extends CI_Controller
{
	//Make everything except index private
	/**
	 * requires 'controller', 'action' parameters, and other parameters depending on the action. 'jsonp' parameter which is the name of the function which handles the response if required.
	 */
	public function index()
	{
		$params = $_REQUEST;
		
		try{
			$this->isAllowed();//throws exception if not allowed
			$controller = ucfirst(strtolower($params['controller']));
			$this->load->model($controller);
			$result['result']=$this->$controller->$params['action']($params);
			//change
			$result['success']=true;
			$json = json_encode($result);
			if(isset($params['jsonp']))
			{
				echo $this->getJsonp($params['jsonp'],$json);
			}
			else {
				echo $json;
			}
		}
		catch(Exception $e)
		{
			$result['success']=false;
			$result['errmsg']= $e->getMessage();
			$json = json_encode($result);
			if(isset($params['jsonp']))
			{
				echo $this->getJsonp($params['jsonp'],$json);
			}
			else {
				echo $json;
			}
		}
	}
	private function getJsonp($fname,$json)
	{
		return $fname.'('.$json.');';
	}
	public function actionSwitcher($params)
	{
		
	}
	private function isAllowed()
	{
		$params = $_REQUEST;
		//try{
			if($this->isAuthentic(0,0))//TODO
			{
				if(!isset($params['controller']))
				{
					throw new Exception('Controller not specified');
				}
				$controller=ucfirst(strtolower($params['controller']));
				if(!isset($params['action']))
				{
					throw new Exception('Action not specified');
				}
				$action=strtolower($params['action']);
				if(!$this->controllerAllowed($controller))
				{
					throw new Exception('Controller not available');
				}
				if(!$this->actionAllowed($action))
				{
					throw new Exception('Action not available');
				}
			}
			else
			{
				throw new Exception('Authentication failed');
			}
	}
	private function controllerAllowed($controller)
	{
		return true;
		return in_array($controller,$this->getAllowedControllers());
	}
	private function actionAllowed($action)
	{
		//return in_array($action,$this->allowedActions());
		return true; //TODO
	}
	private function getAllowedActions()
	{
		$allowed = array('index');
		return $allowed;
	}
	private function getAllowedControllers()
	{
		$allowed = array('facebook_tools','User','Reply');
		return $allowed;
	}
	private function isAuthentic($appid,$key)//TODO
	{
		$apps = array('APP001'=>'252a36ac6c9423d946cd12d19c6a2632');
		return true;
	}
}
