$scope.api.setViewportMetaDefaultForMobileAwareSites = function()
{
	$scope.api.setViewportMetaForMobileAwareSites($scope.model.VIEWPORT_MOBILE_DEFAULT);
}

//JSDOC is available in htmlHeaders.js
$scope.api.setViewportMetaForMobileAwareSites = function(viewportDefType)
{
	var viewportContent;
	switch (viewportDefType) {
		case $scope.model.VIEWPORT_MOBILE_DENY_ZOOM:
			viewportContent = "width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0";
			break;
		case $scope.model.VIEWPORT_MOBILE_DENY_ZOOM_OUT:
			viewportContent = "width=device-width, initial-scale=1.0, minimum-scale=1.0";
			break;
		case $scope.model.VIEWPORT_MOBILE_DENY_ZOOM_IN:
			viewportContent = "width=device-width, initial-scale=1.0, maximum-scale=1.0";
			break;
		case $scope.model.VIEWPORT_MOBILE_DEFAULT:
		default:
			viewportContent = "width=device-width, initial-scale=1.0";
	}

	$scope.api.replaceHeaderTag("meta", "name", "viewport",
			{	tagName: "meta",
				attrs: [ { name: "name", value: "viewport" },
				         { name: "content", value: viewportContent } ]	}
	);
}

//JSDOC is available in htmlHeaders.js
$scope.api.replaceHeaderTag = function(tagName, attrNameToFind, attrValueToFind, newTag)
{
	if (!$scope.model.contributedTags && newTag) $scope.model.contributedTags = [];
	
	if (tagName) for (var i = 0; i < $scope.model.contributedTags.length; i++) {
		var tag = $scope.model.contributedTags[i];
		if (tag && tag.tagName === tagName) {
			if (tag.attrs) for (var j = 0; j < tag.attrs.length; j++) {
				if (tag.attrs[j].name === attrNameToFind && tag.attrs[j].value === attrValueToFind) {
					if (newTag) $scope.model.contributedTags[i] = newTag;
					else $scope.model.contributedTags.splice(i, 1);
					
					return tag;
				}
			}
		}
	}
	
	// no tag was matched, so just add it to the end if needed
	if (newTag) $scope.model.contributedTags.push(newTag);
}

$scope.api.cleanup = function()
{
	$scope.model.contributedTags = null;
}

$scope.model.contributedTags = []; // so that the bind-once watch gets remove on the client even if there are no headers set (undefined -> [] will remove the watch)

$scope.api.addFormStyleClass = function(formname,styleclass)
{
	if (!$scope.model.styleclasses) $scope.model.styleclasses = [];
	var found = false;
	for (var i = 0;i< $scope.model.styleclasses.length;i++)
	{ 
		if (formname == $scope.model.styleclasses[i].formname)
		{
			found = true;
			$scope.model.styleclasses[i].styleclass += ' '+styleclass;
			break;
		}	 
	}
	if (!found)
	{
		$scope.model.styleclasses[$scope.model.styleclasses.length] = {'formname' : formname, 'styleclass' : styleclass};
	}
}

$scope.api.getFormStyleClass = function(formname)
{
	if ($scope.model.styleclasses) 
	{
		for (var i = 0;i< $scope.model.styleclasses.length;i++)
		{ 
			if (formname == $scope.model.styleclasses[i].formname)
			{
				return $scope.model.styleclasses[i].styleclass ;
			}	 
		}
	}	
	return null;	
}

$scope.api.removeFormStyleClass = function(formname,styleclass)
{
	if ($scope.model.styleclasses) 
	{
		for (var i = 0;i< $scope.model.styleclasses.length;i++)
		{ 
			if (formname == $scope.model.styleclasses[i].formname)
			{
				var arr = $scope.model.styleclasses[i].styleclass.split(" ");
				var index = arr.indexOf(styleclass);
				if (index >= 0)
				{
					arr.splice(index, 1);
					if (arr.length == 0)
					{
						for(var j = i; j < $scope.model.styleclasses.length - 1; j++) {
							$scope.model.styleclasses[j] = $scope.model.tabs[j + 1];
						}
						$scope.model.styleclasses.length = $scope.model.styleclasses.length - 1;
					}
					else
					{
						$scope.model.styleclasses[i].styleclass = arr.join(" ");
					}	
				}
				break;
			}
		}
	}	
}