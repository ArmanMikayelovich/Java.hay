function deleteTopic() {
    let topicId = document.getElementById("deleteTopicId").value;
    var topic = {

    };
    make(topic);


    function make(topic) {
        topic.topicId = topicId;
        topic.topicName = '';
    };

    $.ajax({
        url: '/exams/topics/delete',
        contentType : 'application/json; charset=utf-8',
        dataType:'json',
        method: 'DELETE',
        data: JSON.stringify(topic),

        complete: function(response,textStatus) {
            return alert("Hey: " +response.responseText);
        }

    })

}

