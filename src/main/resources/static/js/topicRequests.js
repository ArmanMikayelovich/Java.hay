"use strict";
function deleteTopic() {
    let topicId = document.getElementById("deleteTopicId").value;
    let topic =  makeTopicDto(topicId,null);

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
//make JS object TopicDto
function makeTopicDto(id, name) {
    let topic = {topicId :id,topicName : name};
    topic.topicId = id;
    topic.topicName = name;
    return topic
}


function createTopic() {
    let topicName = document.getElementById("createTopicName").value;
    let topic = makeTopicDto(null,topicName);
    $.ajax({
        url: '/exams/topics/create',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        method: 'POST',
        data: JSON.stringify(topic),

        complete: response => {
            console.log(response);
        }

    });
}

