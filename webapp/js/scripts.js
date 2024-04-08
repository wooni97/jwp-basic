// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);

  document.getElementById('qna-comment-count').innerHTML = "<strong>" + json.countOfComment + "</strong>개의 의견";
}

function onError(xhr, status) {
  alert("error");
}

$('.link-delete-article').click(deleteAnswer);

function deleteAnswer(e) {
  console.log("삭제");
  e.preventDefault();

  var deleteButton = $(this);

  var href = $(this).attr('href');
  var answerId = href.match(/\{(\d+)\}/)[1]; // 문자열을 '/'로 분할하여 마지막 요소를 가져옵니다.
  var queryString = "answerId=" + answerId;

  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : function(json, status) {
      var result = json.result;
      if (result.status) {
        deleteButton.closest('article').remove();
        console.log('삭제 요청이 성공했습니다.');
      }

    },
  })
}
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};