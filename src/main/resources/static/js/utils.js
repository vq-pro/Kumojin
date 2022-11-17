function element(id)
{
    return document.querySelector('#' + id);
}

function getInputField(id)
{
    return element(id).value;
}

function hideElement(id)
{
    element(id).hidden = true;
}

function setElementContent(id, value)
{
    element(id).innerHTML = value;
}

function setInputField(id, value)
{
    element(id).value = value;
}

function showElement(id)
{
    element(id).hidden = false;
}
