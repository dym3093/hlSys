Ext.onReady(function(){
				Ext.QuickTips.init();
				Ext.BLANK_IMAGE_URL='../images/s.gif';
				var tools = [{
			        id:'close',
			        handler: function(e, target, panel){
			            panel.ownerCt.remove(panel, true);
			        }
			    }];
			   
			    function unresolvedRender(questionTitle, metadata, record){
					return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/answer/questionDetailControl!getQuestionDetail.action?questionId=' + record.json.id + '" target="_blank">' + questionTitle + '</a>';
				}
			    function videos(questionTitle, metadata, record){
					return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/video/askSupport.action?username=' + record.json.id + '&askUid='+record.json.id+'" target="_blank">' + questionTitle + '</a>';
				}
				function unresolvedRender(questionTitle, metadata, record){
					return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/answer/questionDetailControl!getQuestionDetail.action?questionId=' + record.json.id + '" target="_blank">' + questionTitle + '</a>';
				}
			    function queryOpinionList(knowledgeName, metadata, record){
					return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/knowledge/knowledge/kopinion!queryOpinionList.action?baseId=' + record.json.baseid + '&knowledgeId='+record.json.kbaseid+'" target="_blank">' + knowledgeName + '</a>';
				}
				function sheetvideos(questionTitle, metadata, record){
				   if( questionTitle=='故障工单(组内待处理)'){
					 return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/sheet/sheetGridForTeam_.action" target="_blank">' + questionTitle + '</a>';
					}else if(questionTitle=='知识审核工单(组内待处理)'){
					 return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/sheet/auditingSheetGridForTeam_.action" target="_blank">' + questionTitle + '</a>';
					}else if(questionTitle=='技术支援服务工单'){
					
					 return '<a style="text-decoration:none;color:#3366cc;font-weight:bold;font-family:verdana;" href="' + ts.appPath + '/wfworksheet/tsservicesheet/dealMain.do?method=performListsendandreciundo" target="_blank">' + questionTitle + '</a>';
					}
				
				}
		
			    var rankStore= new Ext.data.Store({
			    
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/statForHomePeople.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['userid','kwritecounter'])
				});
				rankStore.load();
				
				var rankForExStore = new Ext.data.Store({
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/statForHomeExpert.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['userid','score'])
				});
				rankForExStore.load() ;
				
				var todayMeeting = new Ext.data.Store({
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!meeting.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['meetingTitle','meetingDescription'])
				});
				todayMeeting.load() ;
			   
			   var dealSheet = new Ext.data.Store({
			        url:ts.appPath + 'sheet/sheetGridForPerson_.action',
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!dealSheet.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['sheettype','countSheet'])
				});
				dealSheet.load();
				
				var weekCountKm = new Ext.data.Store({
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!weekCountKm.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['baseid','kbaseid','knowledgeName','byreferencecounter'])
				});
				weekCountKm.load() ;
				
				var findResolvedQuestions = new Ext.data.Store({
				    url:ts.appPath + '/answer/personControl!findUnresolvedQuestions.action',
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!findResolvedQuestions.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['questionTitle','replyCount'])
				});
				findResolvedQuestions.load();
				
				var dutyToday = new Ext.data.Store({
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!dutyToday.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['username','starttime','endtime'])
				});
				dutyToday.load();
				
				var specilHelp = new Ext.data.Store({
					proxy	: 	new Ext.data.HttpProxy({url: ts.appPath+'/kmhome/todaymeeting!specilHelp.action'}),
					reader	: 	new Ext.data.JsonReader(
					{root:'data'},
					['realName','position'])
				});
				specilHelp.load();
				var viewport = new Ext.Viewport({
					layout:'border',
					items:[
						{
							xtype:'portal',
							region:'west',
							collapsible:false,
							width:'19%',
							margins:'0 0 0 0',
							baseCls:'border',
							layoutConfig:{
								animate:true
							},
							items:[{
					               columnWidth:1,
					               style:'padding:3px 0 10px 10px',
					               items:[{
						           		title: '专家积分排行',
						                tools: tools,
						                height:159,
						                layout:'fit',
						                autoScroll:true,
						                items:new Ext.grid.GridPanel({
						                	store:rankForExStore,
						                	cm:new Ext.grid.ColumnModel([{
						                		header:'用户名',
						                		dataIndex:'userid',
						                		width:154
						                	},{
						                		header:'积分',
						                		dataIndex:'score',
						                		width:70
						                	}]),
						                	width:224,
						                	height:150,
						                	trackMouseOver:true,
									        loadMask: {msg:'加载中......'},
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           },{
						                title: '用户发表知识数排行',
						                tools: tools,
						                height:159,
						                layout: 'fit',
						                autoScroll:true,
						                items:new Ext.grid.GridPanel({
						                	xtype: 'grid',
						                	store:rankStore,
						                	cm:new Ext.grid.ColumnModel([
						                		{header:'用户名',sortable:false,width:154,dataIndex:'userid'},
						                		{header:'发表知识',sortable:false,width:70,dataIndex:'kwritecounter'}			                		
				                			]),
						                	width:224,
						                	height:150,
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           },{
						           		title: '今日会议',
					                    tools: tools,
					                    height:175,
					                    layout: 'fit',
					                    autoScroll:true,
					                    items:new Ext.grid.GridPanel({
						                	xtype: 'grid',
						                	store:todayMeeting,
						                	cm:new Ext.grid.ColumnModel([
						                		{header:'主题',dataIndex:'meetingTitle'},
						                		{header:'描述',dataIndex:'meetingDescription'}			                		
				                			]),
						                	width:224,
						                	height:150,
									        viewConfig: {
									            forceFit:true
									        }
						                })
					                    
						           }]
						      }]
						},{
							xtype:'portal',
							region:'center',
							baseCls:'border',
							width:'70%',
							items:[{
					               columnWidth:.75,
					               style:'padding:3px 0 10px 10px',
					               items:[{
					                    title: '待处理工单',
					                    tools: tools,
					                    height:159,
					                    layout:'fit',
					                    autoScroll:true,
					                    items:new Ext.grid.GridPanel({
						                	cm:new Ext.grid.ColumnModel([{
						                		header:'工单类型',
						                		dataIndex:'sheettype',
						                		renderer:sheetvideos
						                	},{
						                		header:'工单数',
						                		dataIndex:'countSheet',
						                		width:80
						                	}]),
						                	ds:dealSheet,
						                	width:730,
						                	height:150,
						                	trackMouseOver:true,
									        loadMask: {msg:'加载中......'},
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           },{
						                title: '本周知识引用排行',
						                tools: tools,
						                height:159,
						                layout:'fit',
						                autoScroll:true,
						                items:new Ext.grid.GridPanel({
						                	xtype: 'grid',
						                	store:weekCountKm,
						                	cm:new Ext.grid.ColumnModel([
						                	{header:'知识名称',dataIndex:'knowledgeName'},
						                	{header:'引用次数',dataIndex:'byreferencecounter'}			                		
				                			]),
				                			ds:weekCountKm,
						                	width:224,
						                	height:150,
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           },{
						           		title: '最新问答',
						                tools: tools,
						                height:175,
						                layout:'fit',
						    			items:new Ext.grid.GridPanel({
						                	xtype: 'grid',
						                	store:findResolvedQuestions,
						                	cm:new Ext.grid.ColumnModel([
						                	{
						                	  header:'问题',
						                	  dataIndex:'questionTitle',
						                	  renderer:unresolvedRender
						                	 },
						                	{
						                	 header:'回复数',
						                	 dataIndex:'replyCount'
						                	}			                		
				                			]),
				                			ds:findResolvedQuestions,
											trackMouseOver:true,
						                	width:224,
						                	height:150,
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           }]
						      },{
					               columnWidth:.25,
					               style:'padding:3px 10px 10px 10px',
					               items:[{
					                    title: '今日值班',
					                    tools: tools,
					                    height:159,
					                    layout:'fit',
					                    autoScroll:true,
					                    items:new Ext.grid.GridPanel({
						                	cm:new Ext.grid.ColumnModel([{
						                		header:'人员名称',
						                		dataIndex:'username',
						                		width:300
						                	},{
						                		header:'开始时间',
						                		dataIndex:'starttime',
						                		width:180
						                	},{
						                		header:'结束时间',
						                		dataIndex:'endtime',
						                		width:180
						                	}]),
						                	ds:dutyToday,
						                	width:730,
						                	height:150,
						                	trackMouseOver:true,
									        loadMask: {msg:'加载中......'},
									        viewConfig: {
									            forceFit:true
									        }
						                })
						           },{
					                    title: '在线专家',
					                    tools: tools,
					                    height:357,
					                    layout:'fit',
						                items:new Ext.grid.GridPanel({
						                	cm:new Ext.grid.ColumnModel([{
						                		header:'专家名称',
						                		dataIndex:'realName',
						                		renderer:videos,
						                		width:300
						                	},{
						                		header:'职位',
						                		dataIndex:'position',
						                		width:500
						                	}]),
						                	ds:specilHelp,
						                	width:730,
						                	height:150,
						                	trackMouseOver:true,
									        loadMask: {msg:'加载中......'},
									        viewConfig: {
									            forceFit:true
									        }
						                })						           
						            }]
						      }]
						}
					],
					renderTo:Ext.getBody()
				});
			});