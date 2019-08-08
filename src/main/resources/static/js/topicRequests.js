function deleteTopic() {
    let id = document.getElementById("deleteTopicId").value;
    $.ajax({
        url: '/oracle-exams/topics/' + id,
        method: 'DELETE',
        error: function (error) {
            alert(JSON.stringify(error));
        },
        success: function (success) {
            document.body.innerHTML = success;

        }
    })
}