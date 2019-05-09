<!DOCTYPE html>
<html>
<head><title>SpringBoot</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>SpringBoot</title>
    <style>
        html {
            font-family: arial, sans-serif;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {
            background-color: #dddddd;
        }
        .container-footer {
            padding: 30px 50px;
            margin-top: 50px;
            background-image: linear-gradient(to top, #2c2929 0%, #313130 100%);
            color: #FFF;
        }
        .container-header {
            padding: 30px 50px;
            margin-bottom: 50px;
            background-image: linear-gradient(to top, #2c2929 0%, #313130 100%);
            color: #FFF;
        }
    </style>
</head>

<header class="container-header text-center bg-black">Prepared by Raziman for interview purposes</header>

<body>

<div class="container gallery-container">

    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Standard Chartered Interview</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Upload Document</a></li>
                <li><a href="/signdoc/">Sign Document</a></li>
                <li ><a href="/genkey/">Generate Key</a></li>
            </ul>
        </div>
    </nav>

<h3>Request Details</h3>
<table>
    <tr>
        <td width="250">Request ID:</td>
        <td>${lists.id}</td>
    </tr>
    <tr>
        <td>
            Requester Email
        </td>
        <td>
            ${lists.req_email}
        </td>
    </tr>
    <tr>
        <td>
            Requester Name
        </td>
        <td>
            ${lists.req_name}
        </td>
    </tr>
    <tr>
        <td>Signer Email
        </td>
        <td>
            ${lists.sign_email}
        </td>
    </tr>
    <tr>
        <td>
            Signer Name
        </td>
        <td>
            ${lists.sign_name}
        </td>
    </tr>
    <tr>
        <td>Request Title:</td>
        <td>${lists.doc_title}</td>
    </tr>
    <tr>
        <td>Type:</td>
        <td>${lists.doc_type}</td>
    </tr>
    <tr>
        <td>
            Document MD5
        </td>
        <td>
            ${lists.doc_md5}
        </td>
    </tr>
    <tr>
        <td>
            Request Status
        </td>
        <td>
            ${lists.doc_status}
        </td>
    </tr>
    <tr>
        <td>Deadline:</td>
        <td>${lists.doc_deadline}</td>
    </tr>
    <tr>
        <td>Signature:</td>
        <td>${lists.doc_signature}</td>
    </tr>
    <tr>
        <td>Date Created:</td>
        <td>${lists.req_date_created}</td>
    </tr>
    <tr>
        <td>Date Modified:</td>
        <td>${lists.req_date_modified}</td>
    </tr>
    <tr>
        <td>
            Time Created:
        </td>
        <td>
            ${lists.req_time_created}
        </td>
    </tr>
</table>
</div>
</body>

<footer class="container-footer text-center bg-black">Prepared by Raziman for interview purposes</footer>

</html>