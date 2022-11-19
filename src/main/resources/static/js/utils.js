function clearInputField(id)
{
    setInputField(id, '');
}

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

function newElement(type, id, text)
{
    let element = document.createElement(type);
    element.id = id;
    element.textContent = text;

    return element;
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
