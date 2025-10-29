<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>

    <div style="border:2px black solid;width:fit-content; width:-webkit-fit-content; width:-moz-fit-content;">
    <table style="color:black;"><tbody>
    <tr>
    <td class="confluenceTd">
    New changes have been done in the Security Profile. Please find the below details :
    </td>
    </tr>
    </tbody></table>

    <p style="color:black;"><span style="color:black;">Project Name:&nbsp;<b>${ProjectName}</b></span>
        <br><span style="color:black;">Account Name:&nbsp;<b>${AccountName}</b></span>
        <br><span style="color:black;">Updated Content:&nbsp;</span>
            <ul>
                <#list updatedQuestionsList as question>
                  <li><b> ${question.updatedSecurityQuestion}  <br> Answer has been changed from
                  '${question.securityAnswerUpdatedFrom}' to '${question.securityAnswerUpdatedTo}' </b></li>
                </#list>
              </ul>
        <span style="color:black;">Updated By:&nbsp;<b>${UpdatedBy}</b></span></p>
        <p style="color:black;">Thank you!</p>
        </div>
</body>
</html>