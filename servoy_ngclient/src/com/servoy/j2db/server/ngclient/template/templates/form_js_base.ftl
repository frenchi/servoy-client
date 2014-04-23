<#--
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2013 Servoy BV

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU Affero General Public License as published by the Free
 Software Foundation; either version 3 of the License, or (at your option) any
 later version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License along
 with this program; if not, see http://www.gnu.org/licenses or write to the Free
 Software Foundation,Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
-->
<#macro form_js_body>Abstract macro</#macro>
<#macro form_js>
${registerMethod}("${controllerName}", function($scope, $servoyInternal,$timeout,$windowService) {

	var beans = {
	<#list baseComponents as bc>
		${bc.name}: ${bc.propertiesString}<#if bc_has_next>,</#if>
	</#list>
	}

	<#list baseComponents as bc>
		<#list bc.valuelistProperties as vlprop>
	beans.${bc.name}.${vlprop}_filter = function(filterString) {
		if (filterString) {
			return $servoyInternal.filterList('${name}','${bc.name}','${vlprop}',filterString);
		}
		return $scope.model.${bc.name}.${vlprop}
	}
	    </#list>
	</#list>

	var formProperties = ${propertiesString}
	var formState = $servoyInternal.initFormState("${name}", beans, formProperties);
	$scope.model = formState.model;
	$scope.api = formState.api;
	$scope.layout = formState.layout;
	$scope.formStyle = formState.style;
	$scope.formProperties = formState.properties;
	$scope.formname = "${name}";

	<#list parts as part>
	$scope.${part.name}Style = ${part.style};
	</#list>
	
	var getExecutor = function(beanName,eventType) {
		var callExecutor = function(args, svy_pk) {
			return $servoyInternal.getExecutor("${name}").on(beanName,eventType,null,args,svy_pk);
		}
		var wrapper = function() {
			return callExecutor(arguments, null);
		}
		wrapper.selectRecordHandler = function(svy_pk) {
			return function () { return callExecutor(arguments, svy_pk); }
		}
		return wrapper;
	}

	var getApply = function(beanname) {
		var wrapper = function(property, beanModel) {
			$servoyInternal.push("${name}",beanname,property,beanModel);
		}
		return wrapper;
	}
	
	var servoyApi = function(beanname) {
		return {
			setFormVisibility: function(formname, visibility,relationname,formIndex) {
				return $servoyInternal.setFormVisibility(formname, visibility,relationname,$scope.formname, beanname,formIndex);
			},
			setFormEnabled: function(formname, enabled) {
				return $servoyInternal.setFormEnabled(formname, enabled);
			},
			setFormReadOnly: function(formname, readOnly) {
				return $servoyInternal.setFormReadOnly(formname, readOnly);
			},
			getFormUrl: function(formUrl) {
				return $windowService.getFormUrl(formUrl);
			}
		}	
	}

	$scope.handlers = {
	<#list baseComponents as bc>
		${bc.name}: {"svy_apply":getApply('${bc.name}'),"svy_servoyApi":servoyApi('${bc.name}')<#list bc.handlers as handler>,${handler}:getExecutor('${bc.name}', '${handler}')</#list>}<#if bc_has_next>,</#if>
	</#list>
	}
	
	formState.handlers = $scope.handlers;
	
	var wrapper = function(beanName) {
		return function(newvalue,oldvalue) {
			if(oldvalue === newvalue) return;
			$servoyInternal.sendChanges(newvalue,oldvalue, "${name}", beanName);
		}
	}

	<#list parts as part>
		<#if (part.baseComponents)??>
			<#list part.baseComponents as bc>
				$scope.$watch("model.${bc.name}", wrapper('${bc.name}'), true);
			</#list>
		</#if>
	</#list>

<@form_js_body/> 
});
</#macro>
