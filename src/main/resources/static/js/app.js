let urlBackend = "http://localhost:8080/items";

function addItem()
{
    const payload = {
        name: getInputField('name')
    }

    const options = {
        method: 'POST',
        body: JSON.stringify(payload),
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch(urlBackend, options)
        .then((response) =>
        {
            if (response.status === 201)
            {
                getAndDisplayList();

            } else if (response.status === 400)
            {
                setError('Invalid item');

            } else if (response.status === 409)
            {
                setError('Duplicate item');

            } else
            {
                setError('Internal server error (' + response.status + ')')
            }
        })
}

function clearError()
{
    hideElement('error')
}

function clearEvent()
{
    setInputField('name', '');
}

function clearList()
{
    setElementContent('list', '');
}

function displayItem(text)
{
    var element = document.createElement('li');
    element.id = "item";
    element.textContent = text;

    document.querySelector('ul').appendChild(element);
}

function getAndDisplayList()
{
    clearError();
    clearEvent();
    clearList();

    fetch(urlBackend)
        .then(response =>
        {
            return response.json();
        })
        .then(data =>
        {
            for (let item of data.items)
            {
                displayItem(item);
            }
        })
        .catch((error) =>
        {
            alert("Cannot connect to the backend - " + error)
        });
}

function setError(error)
{
    showElement('error')
    setElementContent('error', error);
}
