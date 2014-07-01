name: 'svy-textarea',
displayName: 'Text Area',
definition: 'servoydefault/textarea/textarea.js',
libraries: [],
model:
{
        background : 'color', 
        borderType : 'border', 
        dataProviderID : { 'type':'dataprovider', scope:'design', 'ondatachange': { 'onchange':'onDataChangeMethodID', 'callback':'onDataChangeCallback'}}, 
        editable : {type:'boolean', default:true}, 
        enabled : {type:'boolean', default:true}, 
        fontType : 'font', 
        foreground : 'color', 
        format : {for:'dataProviderID' , type:'format'}, 
        horizontalAlignment : {type:'int', scope:'design', values:[{LEFT:2}, {CENTER:0},{RIGHT:4}],default: 2}, 
        location : 'point', 
        margin : {type:'dimension', scope:'design'}, 
        placeholderText : 'tagstring', 
        scrollbars : {type:'int', scope:'design'}, 
        size : {type:'dimension',  default: {width:140, height:140}}, 
        styleClass : { type:'styleclass', scope:'design', values:['form-control', 'input-sm', 'svy-padding-xs']}, 
        tabSeq : {type:'tabseq', scope:'design'}, 
        text : 'tagstring', 
        toolTipText : 'tagstring', 
        transparent : 'boolean', 
        visible : {type:'boolean', default:true} 
},
handlers:
{
        onActionMethodID : 'function', 
        onDataChangeMethodID : 'function', 
        onFocusGainedMethodID : 'function', 
        onFocusLostMethodID : 'function', 
        onRenderMethodID : 'function', 
        onRightClickMethodID : 'function' 
},
api:
{
        getScrollX: {
            returns: 'int'
        },
        getScrollY: {
            returns: 'int'
        },
        getSelectedText: {
            returns: 'string'
        },
        replaceSelectedText: {
            parameters:[{'s':'string'}]
        },
        requestFocus: {
            parameters:[{'mustExecuteOnFocusGainedMethod':'boolean','optional':'true'}]
        },
        selectAll: {

        },
        setScroll: {
            parameters:[{'x':'int'},{'y':'int'}]
        }
}
 
