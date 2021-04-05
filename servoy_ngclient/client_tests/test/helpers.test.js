describe('styles helpers', function() {
	//jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000;
	var $scope 
	var $compile  
	var $timeout
	var formatFilter
	var mnemonicletterFilter

	beforeEach(function() {
		module('servoy-components')  // generated by ngHtml2JsPreprocessor from all .html template files , as strings in the svyTemplate module
		// 1. Include your application module for testing.
		module('servoy');

		// 2. Define a new mock module. (don't need to mock the servoy module for tabpanel since it receives it's dependencies with attributes in the isolated scope)
		// 3. Define a provider with the same name as the one you want to mock (in our case we want to mock 'servoy' dependency.
//		angular.module('servoyMock', [])
//		.factory('$X', function(){
//		// Define you mock behaviour here.
//		});

		// 4. Include your new mock module - this will override the providers from your original module.
//		angular.mock.module('servoyMock');

		// 5. Get an instance of the provider you want to test.
		inject(function(_$rootScope_,_$compile_ ,$templateCache,_$q_,_$timeout_,_formatFilterFilter_,_mnemonicletterFilterFilter_){

			$compile = _$compile_
			$timeout = _$timeout_
			$scope = _$rootScope_.$new();
			formatFilter = _formatFilterFilter_
			mnemonicletterFilter =  _mnemonicletterFilterFilter_
		})
		// mock timout
		jasmine.clock().install();
	});
	afterEach(function() {
		jasmine.clock().uninstall();
	})

	it("should apply svy-background", function() {
		var template= '<div name="myTag" svy-background="myModel"></div>'
			var divWithSvyBackground = $compile(template)($scope);
		$scope.myModel ='red';
		//check style before
		expect(divWithSvyBackground[0].style.backgroundColor != 'red').toBe(true);   
		$scope.$digest();
		//checks style after
		expect(divWithSvyBackground[0].style.backgroundColor ).toBe('red'); 
		// see if changes in the model are reflected in style
		$scope.myModel ='blue';
		$scope.$digest();
		expect(divWithSvyBackground[0].style.backgroundColor ).toBe('blue');              
	});
	it("should apply svy-foreground", function() {
		var template= '<div name="myTag" svy-foreground="myModel"></div>'
			var myDiv = $compile(template)($scope);
		$scope.myModel ='red';
		//check style before
		expect(myDiv[0].style.color!= 'red').toBe(true);   
		$scope.$digest();
		//checks style after
		expect(myDiv[0].style.color).toBe('red'); 
		// see if changes in the model are reflected in style
		$scope.myModel ='blue';
		$scope.$digest();
		expect(myDiv[0].style.color).toBe('blue');              
	});
	it("should apply svy-border", function() {
		var template= '<div name="myTag" svy-border="myModel"></div>'
			var myDiv = $compile(template)($scope);
		$scope.myModel ={"type":"EtchedBorder","borderStyle":{"borderColor":"#00ff40 #0000a0 #0000a0 #00ff40","borderStyle":"ridge","borderWidth":"2px"}};
		//check style before
		var a = myDiv.css('border-bottom-width');
		var b = myDiv.css('border-bottom-color');
		var c = myDiv.css('border-bottom-style');
		if (a === '0px') a = ''; // this style computes correctly on IE only
		if (b === 'rgb(0, 0, 0)') b = ''; // this style computes correctly on IE only
		if (c === 'none') c = ''; // this style computes correctly on IE only
		expect(a).toBe(''); 
		expect(b).toBe('');
		expect(c).toBe('');
		$scope.$digest();
		//checks style after
		expect(myDiv.css('border-bottom-width')).toBe('2px'); 
		expect(myDiv.css('border-bottom-color')).toBe('rgb(0, 0, 160)');
		expect(myDiv.css('border-bottom-style')).toBe('ridge');
		expect(myDiv.css('border-color')).toBe('rgb(0, 255, 64) rgb(0, 0, 160) rgb(0, 0, 160) rgb(0, 255, 64)'); 
		// see if changes in the model are reflected in style at runtime
		$scope.myModel ={"type":"SpecialMatteBorder","borderStyle":{"borderTop":"3.0px","borderRight":"1.0px","borderBottom":"4.0px","borderLeft":"2.0px","borderTopColor":"#ff0000","borderRightColor":"#000000","borderBottomColor":"#0000ff","borderLeftColor":"#008000","borderRadius":"4.0px","borderStyle":"solid"}};
		$scope.$digest();
		expect(myDiv.css('border-style')).toBe('solid');
		expect(myDiv.css('border-color')).toBe('rgb(255, 0, 0) rgb(0, 0, 0) rgb(0, 0, 255) rgb(0, 128, 0)');
		a = myDiv.css('border-top-left-radius');
		if (a === '4px 4px') a = '4px'; // phantom JS
		expect(a).toBe('4px');

	});
	it("should apply  at design time svy-margin", function() {
		// is is a design time property, model is sent before
		$scope.myModel ={"paddingTop":"4px","paddingBottom":"3px","paddingLeft":"1px","paddingRight":"4px"}
		var template= '<div name="myTag" svy-margin="myModel"/>' 
			var myDiv = $compile(template)($scope);
		$scope.$digest();
		//checks style 
		expect(myDiv[0].style.padding).toBe('4px 4px 3px 1px');  
		// runtime changes should not be affected for design time properties
		$scope.myModel ={"paddingTop":"1px","paddingBottom":"1px","paddingLeft":"1px","paddingRight":"1px"};
		$scope.$digest();

		expect(myDiv[0].style.padding).toBe('4px 4px 3px 1px');   
	});
	it("should apply svy-font", function() {
		var template= '<div name="myTag" svy-font="myModel"></div>' 
			var myDiv = $compile(template)($scope);
		$scope.myModel ={"fontSize":"14px","fontFamily":"Segoe UI Semibold, Verdana, Arial"}
		//check style before
		expect(myDiv[0].style.font).toBe('');   
		$scope.$digest();
		//checks style after
		expect(myDiv[0].style.fontSize).toBe("14px");
		var ff = myDiv[0].style.fontFamily;
		if ("Segoe UI Semibold,Verdana,Arial" === ff) ff = "'Segoe UI Semibold', Verdana, Arial"; // this is how PhantomJS prints it
		if ("Segoe UI Semibold, Verdana, Arial" === ff) ff = "'Segoe UI Semibold', Verdana, Arial"; // this is how PhantomJS prints it
		expect(ff.replace(/"/g, '\'')).toBe("'Segoe UI Semibold', Verdana, Arial");
		// runtime change 
		$scope.myModel ={"fontWeight":"bold","fontSize":"16px","fontFamily":"Comic Sans MS, Verdana, Arial"}
		$scope.$digest();		
		expect(myDiv[0].style.fontSize).toBe("16px");
		ff = myDiv[0].style.fontFamily;
		if ("Comic Sans MS,Verdana,Arial" === ff) ff = "'Comic Sans MS', Verdana, Arial"; // this is how PhantomJS prints it
		if ("Comic Sans MS, Verdana, Arial" === ff) ff = "'Comic Sans MS', Verdana, Arial"; // this is how PhantomJS prints it
		expect(ff.replace(/"/g, '\'')).toContain("'Comic Sans MS', Verdana, Arial");
		expect(myDiv[0].style.fontWeight).toBe("bold");
	});

	it("should apply svy-click,svy-dblclick,svy-rightclick,svy-focusgained,svy-focuslost", function() {
		// is is a design time property, model is sent before
		var events=['click','dblclick','contextmenu','blur','focus']
		var directiveNames= ['svy-click','svy-dblclick','svy-rightclick','svy-focuslost','svy-focusgained']
		for(var i =0; i< events.length;i++){
			var called = false;
			$scope.myHandler = function($event){ 
				if($event){
					called=true
				}else{
					throw "$event argument null"
				}
			}
			var template= '<div name="myTag" '+directiveNames[i]+'="myHandler($event)"></div>' 
			var myDiv = $compile(template)($scope);

			$scope.$digest();
			myDiv.triggerHandler(events[i])
			$timeout.flush(100);
			expect( called).toBe(true);
		}
	});
	it("should apply svy-enter", function() {
		// is is a design time property, model is sent before
		var called = false;
		$scope.myHandler = function($event){ 
			if($event){
				called=true
			}else{
				throw "$event argument null"
			}
		}
		var template= '<input type="text" svy-enter="myHandler($event)">' 
			var myDiv = $compile(template)($scope);
		$scope.$digest();
		myDiv[0].triggerKey(65);
		expect( called).toBe(false);
		myDiv[0].triggerKey(13);
		$timeout.flush(100);
		expect( called).toBe(true);
	});
	it("should apply svy-mnemonic", function() {
		// is is a design time property, model is sent before
		$scope.myModel = 'a'
			var template= '<div svy-mnemonic="myModel"></div>' 
				var myDiv = $compile(template)($scope);
		$scope.$digest();
		expect(myDiv.attr('accesskey')).toBe('a')
		$scope.myModel = 'b'
			$scope.$digest();
		expect(myDiv.attr('accesskey')).toBe('a')
	});
	it("should apply svy-textrotation", function() {
		// is is a design time property, model is sent before
		$scope.myModel = 90;
		$scope.model ={size:{height:50,width:100}};
		var template= '<div svy-textrotation="myModel"  style="width:100px;height:50px"></div>' 
			var myDiv = $compile(template)($scope);
		$scope.$digest();
		jasmine.clock().tick(100); // TODO remove this once the setTimeout is removed from svy-textrotation
		expect(myDiv[0].style.width).toBe('50px')
		expect(myDiv[0].style.height).toBe('100px')
	});
	it("should apply svy-selectonenter", function() { 
		// is is a design time property, model is sent before
		$scope.myModel = true;
		var template= '<input type="text" value="Dummy text" svy-selectonenter="myModel">' 
			var myDiv = $compile(template)($scope);
		$scope.$digest();      
		myDiv.triggerHandler('focus');
		$timeout.flush()

		//expect( window.getSelection().toString()).toBe('Dummy text'); // TODO fix getSelected text
	});
	it("should apply svy-rollovercursor", function() { 
		// is is a design time property, model is sent before
		$scope.myModel = 12;
		var template= '<div  svy-rollovercursor="myModel"/>' 
			var myDiv = $compile(template)($scope);
		$scope.$digest();      
		expect(myDiv[0].style.cursor).toBe('pointer');
	});

	it("should apply svy-format", function() {  //  TODO ?
//		// is is a design time property, model is sent before
//		$scope.myModel = 'abc';
//		$scope.myFormat = {
//		type:"TEXT",
//		isMask:true,
//		edit:"UUU",
//		placeHolder:null,
//		allowedCharacters:null,
//		display:"UUU"
//		} 
//		var template= '<input type="text" ng-model="myModel" svy-format="myFormat">' 
//		var myDiv = $compile(template)($scope);
//		$('body').append(myDiv)
//		$scope.$digest();
//		//expect( $scope.myModel).toBe('ABC');      TODO
//		myDiv.selectRange(0);
//		var e = $.Event('keydown');
//		e.which = 100;	 			
//		myDiv.trigger(e); //d
//		jasmine.clock().tick(100);
//		//myDiv.val('dbc')
//		$scope.$digest();
//		//expect( $scope.myModel).toBe('DBC');       TODO
	});
	it("should filter based on format", function() {  // Implement this test case when
		// is is a design time property, model is sent before
		$scope.myModel = 'abc';
		var ret =formatFilter($scope.myModel,'UUU', 'TEXT');
		expect(ret).toBe('ABC');	  	
	});
	it("should undernine mnemonic based on parameter letter", function() {  // Implement this test case when
		// is is a design time property, model is sent before
		$scope.myModel = 'abc';
		$scope.mnemonic = 'b'
			var ret =mnemonicletterFilter($scope.myModel,'b');
		expect(ret.toString()).toBe('a<u>b</u>c');	  	
	});
}); 
