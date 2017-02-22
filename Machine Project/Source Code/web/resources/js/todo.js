var selectedListID = null;
var selectedTaskID = null;
var currOpaqueList = null;
var currOpaqueTask = null;
var countdown = null;
function postRequestToServlet(url, action, listID, taskID, callback, order) {
	$.post(url, {action: action, listID: listID, taskID: taskID, order: order}, callback);
}

$(document).ready(function(){
	init();
	$("#todo-list-col").on("click", "li.list-group-item", function(){
		$("#task-desc-form").slideUp(0);
		if (currOpaqueTask != null) {
			currOpaqueTask.css("opacity", 1);
		}
		if (currOpaqueList != null) {
			currOpaqueList.css("opacity", 1);
		}
		$(this).css("opacity", 0.3);
		currOpaqueList = $(this);
		selectedListID = $(this).val();
		showTaskListGroup($("#tasks-list-group"), selectedListID);
	});
	
	$("#tasks-col").on("click", "li.list-group-item", function(){
		if (currOpaqueTask != null) {
			currOpaqueTask.css("opacity", 1);
		}
		$(this).css("opacity", 0.3);
		currOpaqueTask = $(this);
		selectedTaskID = $(this).val();
		showTaskDesc(selectedTaskID);
	});
	
	$("#list-order").change(function(){
		showTodoListListGroup($("#todo-lists-list-group"), false);
	});
	
	$("#edit-todo-list-btn").click(function(){
		if (selectedListID != null) {
			window.location.replace("editlist.jsp?listID=" + selectedListID);
		}	
	});
	
	$("#delete-todo-list-btn").click(function(){
		if (selectedListID != null) {
			postRequestToServlet("TodoListServlet", "DELETE", selectedListID);
			window.location.replace("todo.jsp");
		}
	}); 
	
	$("#task-order").change(function(){
		showTaskListGroup($("#tasks-list-group"), selectedListID);
	});
	
	$("#add-task-btn").click(function(){
		if (selectedListID != null) {
			window.location.replace("addtask.jsp?listID=" + selectedListID);
		}	
	});
	
	$("#edit-task-btn").click(function(){
		if (selectedListID != null && selectedTaskID != null) {
			window.location.replace("edittask.jsp?listID=" + selectedListID + "&taskID=" + selectedTaskID);
		}	
	});
	
	$("#delete-task-btn").click(function(){
		if (selectedTaskID != null) {
			postRequestToServlet("TaskServlet", "DELETE", selectedListID, selectedTaskID,
				function(){
					//reload the task list
					$("#task-desc-form").slideUp("slow");
					showTaskListGroup($("#tasks-list-group"), selectedListID);
				}
			);
			
		}
	});
	
	$("#toggle-finished-btn").click(function() {
		if (selectedTaskID != null) {
			postRequestToServlet("TaskServlet", "TOGGLE_FINISHED", selectedListID, selectedTaskID,
				function(){ 
					// reload the task list
					showTaskListGroup($("#tasks-list-group"), selectedListID);
					// relouad the task description
					showTaskDesc(selectedTaskID);
				}
			);	
		}
	});
	
	$("#remove-finished-btn").click(function(){
		postRequestToServlet("TodoListServlet", "REMOVE_FINISHED", selectedListID, selectedTaskID,
				function(){
					//reload the task list
					$("#task-desc-form").slideUp("slow");
					showTaskListGroup($("#tasks-list-group"), selectedListID);
				}
		);
	});
});

function init() {
	$("#task-desc-form").slideUp(0);
	showTodoListListGroup($("#todo-lists-list-group"), true);
}

function showTodoListListGroup(listGroup, isStart) {
	var speed = (isStart) ? 0 : "slow";
	listGroup.slideUp(speed,function() {
		postRequestToServlet("TodoListServlet", "GET",  null, null, function(responseJSON) {
			listGroup.empty();
			$.each(responseJSON, function(index, item) {
				addToListGroup(listGroup, item);
			});
			listGroup.slideDown(speed);
		}, $("#list-order").val());
	});
}

function showTaskListGroup(listGroup, listID) {
	listGroup.slideUp("slow",function() {
		postRequestToServlet("TaskServlet", "GET", listID, null, function(responseJSON) {
			listGroup.empty();
			$.each(responseJSON, function(index, item) {
				addToListGroup(listGroup, item);
			});
			listGroup.slideDown("slow");
		}, $("#task-order").val());
	});
}

function addToListGroup(listGroup, item, color) {
	var str;
	if (item.isFinished) {
		str = "<li class=\"list-group-item\" value =\"" 
			+ item.id + "\" style=\"background-color:" 
			+ item.backgroundColor + ";color:" + item.foregroundColor 
			+ "\"><del>" + item.name + "</del></li>";
	} else {
		str = "<li class=\"list-group-item\" value =\"" 
			+ item.id + "\" style=\"background-color:" 
			+ item.backgroundColor + ";color:" + item.foregroundColor 
			+ "\">" + item.name + "</li>";
	}
	listGroup.append(str);
}

function showTaskDesc(taskID) {
	if (countdown != null) {
		countdown.stop();
	}
	var listGroup = $("#task-desc-form");
	listGroup.slideUp("slow", function(){
		postRequestToServlet("TaskDescriptionServlet",
				"GET", selectedListID, taskID, 
				function(responseJSON) {
					$("#task-desc-form").css("background-color", responseJSON.backgroundColor)
						.css("color", responseJSON.foregroundColor);
					$("#task-desc-name").html(responseJSON.name);
					$("#task-desc-finished").html((responseJSON.isFinished) ? "YES" : "NO");
					$("#task-desc-priority").html(responseJSON.priority);
					$("#task-desc-description").html(responseJSON.description);
					$("#task-desc-due-date").html(responseJSON.dateTimeStr);
					countdown = new Countdown($("#task-desc-countdown"), responseJSON.dateTimeStr);
					countdown.start();
				} );
		listGroup.slideDown();
	});	
}
