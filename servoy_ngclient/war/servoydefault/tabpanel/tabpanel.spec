name: 'svy-tabpanel',
displayName: 'Tab panel',
definition: 'servoydefault/tabpanel/tabpanel.js',
model:
{
        background : 'color', 
        borderType : 'border', 
        closeOnTabs : 'boolean', 
        enabled : 'boolean', 
        fontType : 'font', 
        foreground : 'color', 
        horizontalAlignment : {type:'int', values:[{DEFAULT:-1}, {LEFT:0}, {CENTER:2},{RIGHT:4}]}, 
        location : 'point', 
        scrollTabs : 'boolean', 
        selectedTabColor : 'color', 
        size : 'dimension', 
        styleClass : 'string', 
        tabOrientation : 'int', 
        tabSeq : 'tabseq', 
        transparent : 'boolean', 
        visible : 'boolean',
        tabs : 'tab[]'  
},
handlers:
{
        onChangeMethodID : 'function', 
        onTabChangeMethodID : 'function' 
},
api:
{
        addTab:{
            returns: 'boolean',
            parameters:[{'form/formname':'object []'},{'name':'object','optional':'true'},{'tabText':'object','optional':'true'},{'tooltip':'object','optional':'true'},{'iconURL':'object','optional':'true'},{'fg':'object','optional':'true'},{'bg':'object','optional':'true'},{'relatedfoundset/relationname':'object','optional':'true'},{'index':'object','optional':'true'}]
        }, 
        getAbsoluteFormLocationY:{
            returns: 'int',
                 }, 
        getClientProperty:{
            returns: 'object',
            parameters:[{'key':'object'}]
        }, 
        getDesignTimeProperty:{
            returns: 'object',
            parameters:[{'unnamed_0':'string'}]
        }, 
        getElementType:{
            returns: 'string',
                 }, 
        getHeight:{
            returns: 'int',
                 }, 
        getLocationX:{
            returns: 'int',
                 }, 
        getLocationY:{
            returns: 'int',
                 }, 
        getMaxTabIndex:{
            returns: 'int',
                 }, 
        getMnemonicAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getName:{
            returns: 'string',
                 }, 
        getSelectedTabFormName:{
            returns: 'string',
                 }, 
        getTabBGColorAt:{
            returns: 'string',
            parameters:[{'unnamed_0':'int'}]
        }, 
        getTabFGColorAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getTabFormNameAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getTabNameAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getTabRelationNameAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getTabTextAt:{
            returns: 'string',
            parameters:[{'i':'int'}]
        }, 
        getWidth:{
            returns: 'int',
                 }, 
        isTabEnabled:{
            returns: 'boolean',
            parameters:[{'unnamed_0':'int'}]
        }, 
        isTabEnabledAt:{
            returns: 'boolean',
            parameters:[{'i':'int'}]
        }, 
        putClientProperty:{
            
            parameters:[{'key':'object'},{'value':'object'}]
        }, 
        removeAllTabs:{
            returns: 'boolean',
                 }, 
        removeTabAt:{
            returns: 'boolean',
            parameters:[{'index':'int'}]
        }, 
        setLocation:{
            
            parameters:[{'x':'int'},{'y':'int'}]
        }, 
        setMnemonicAt:{
            
            parameters:[{'index':'int'},{'text':'string'}]
        }, 
        setSize:{
            
            parameters:[{'width':'int'},{'height':'int'}]
        }, 
        setTabBGColorAt:{
            
            parameters:[{'unnamed_0':'int'},{'unnamed_1':'string'}]
        }, 
        setTabEnabled:{
            
            parameters:[{'unnamed_0':'int'},{'unnamed_1':'boolean'}]
        }, 
        setTabEnabledAt:{
            
            parameters:[{'i':'int'},{'b':'boolean'}]
        }, 
        setTabFGColorAt:{
            
            parameters:[{'i':'int'},{'s':'string'}]
        }, 
        setTabTextAt:{
            
            parameters:[{'index':'int'},{'text':'string'}]
        } 
},
types: {
  tab: {
  	model: {
  		containsFormId: 'form',
  		text: 'tagstring',
  		relationName: 'relation',
  		active: 'boolean',
  		foreground: Color
  	}
  }
}