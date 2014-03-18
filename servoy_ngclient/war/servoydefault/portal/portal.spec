name: 'svy-portal',
displayName: 'Portal',
definition: 'servoydefault/portal/portal.js',
model:
{
        background : 'color', 
        borderType : 'border', 
        enabled : 'boolean', 
        foreground : 'color', 
        initialSort : 'string', 
        intercellSpacing : 'dimension', 
        location : 'point', 
        multiLine : 'boolean', 
        relationName : 'string', 
        reorderable : 'boolean', 
        resizable : 'boolean', 
        resizeble : 'boolean', 
        rowBGColorCalculation : 'string', 
        rowHeight : 'int', 
        scrollbars : 'int', 
        showHorizontalLines : 'boolean', 
        showVerticalLines : 'boolean', 
        size : 'dimension', 
        sortable : 'boolean', 
        styleClass : 'string', 
        tabSeq : 'tabseq', 
        transparent : 'boolean', 
        visible : 'boolean' 
},
handlers:
{
        onDragEndMethodID : 'function', 
        onDragMethodID : 'function', 
        onDragOverMethodID : 'function', 
        onDropMethodID : 'function', 
        onRenderMethodID : 'function' 
},
api:
{
        deleteRecord:{
            
                 }, 
        duplicateRecord:{
            
            parameters:[{'addOnTop':'boolean','optional':'true'}]
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
        getMaxRecordIndex:{
            returns: 'int',
                 }, 
        getName:{
            returns: 'string',
                 }, 
        getScrollX:{
            returns: 'int',
                 }, 
        getScrollY:{
            returns: 'int',
                 }, 
        getSelectedIndex:{
            returns: 'int',
                 }, 
        getSortColumns:{
            returns: 'string',
                 }, 
        getWidth:{
            returns: 'int',
                 }, 
        newRecord:{
            
            parameters:[{'addOnTop':'boolean','optional':'true'}]
        }, 
        putClientProperty:{
            
            parameters:[{'key':'object'},{'value':'object'}]
        }, 
        setLocation:{
            
            parameters:[{'x':'int'},{'y':'int'}]
        }, 
        setScroll:{
            
            parameters:[{'x':'int'},{'y':'int'}]
        }, 
        setSelectedIndex:{
            
            parameters:[{'index':'int'}]
        }, 
        setSize:{
            
            parameters:[{'width':'int'},{'height':'int'}]
        } 
}