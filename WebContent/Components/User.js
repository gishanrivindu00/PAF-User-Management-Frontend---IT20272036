//Hide the alerts on page load
$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});
// SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation
	var status = validateUserForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }	
	// If valid
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
		{
			url : "UsersAPI",
			type : type,
			data : $("#formUser").serialize(),
			dataType : "text",
			complete : function(response, status)
			{
				onUserSaveComplete(response.responseText, status);
			}
		});
});

// UPDATE
$(document).on("click", ".btnUpdate", function(event)
{		
	
	 $("#hidUserIDSave").val($(this).data("userid"));
	 $("#name").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#email").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#address").val($(this).closest("tr").find('td:eq(2)').text());
});

//DELETE
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
		{
			url : "UsersAPI",
			type : "DELETE",
			data : "userID=" + $(this).data("userid"),
			dataType : "text",
			complete : function(response, status)
			{
				onUserDeleteComplete(response.responseText, status);
			}
		});
});

// CLIENT-MODEL
function validateUserForm()
{
	// NAME
	if ($("#name").val().trim() == "")
	 {
		return "Insert User Name.";
	 }
	// EMAIL
	if ($("#email").val().trim() == "")
	 {
		return "Insert User Email.";
	 }
	//ADDRESS
	if ($("#address").val().trim() == "")
	 {
		return "Insert User Address.";
	 }
	return true;
}
function onUserSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divUsersGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidUserIDSave").val("");
	$("#formUser")[0].reset();
}
function onUserDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divUsersGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}