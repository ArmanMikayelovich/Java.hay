function deleteTopic() {
    let topicId = document.getElementById("deleteTopicId").value;
    var topic = {};
    make(topic);


    $.ajax({
        url: '/exams/topics/delete',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        method: 'DELETE',
        data: JSON.stringify(topic),

        complete: response => {
            console.log(response);
        }

    })

}

function makeTopic(id, name) {
    let topic;
    topic.topicId = topicId;
    topic.topicName = '';
    return topic
}

function createTopic() {
    let topicName = document.getElementById("createTopicName").value;
    var topic = make(null,topicName);



}

