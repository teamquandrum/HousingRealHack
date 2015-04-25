<?php
class Paramchecker
{
	/**
	* @param $required array of params required to be set
	* @param $params array of params to be checked
	* @return true if all values in $required is present in $params, else false
	*/
	public function isParamsSet($required,$params)
	{
		foreach($required as $p)
		{
			if(!isset($params[$p]))
				return false;
		}
		return true;
	}	
}
?>
