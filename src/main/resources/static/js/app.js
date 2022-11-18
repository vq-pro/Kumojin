let urlBackend = "http://localhost:8080/events";

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

function clearList()
{
    document.querySelector('table').innerHTML = '';
}

function clearNewEvent()
{
    setInputField('name', '');
}

function displayEvent(event)
{
    var tdName = document.createElement('td');
    tdName.id = "names";
    tdName.textContent = event.name;
    var tdDesc = document.createElement('td');
    tdDesc.id = "descs";
    tdDesc.textContent = event.description;

    var tr = document.createElement('tr');
    tr.appendChild(tdName);
    tr.appendChild(tdDesc);

    document.querySelector('table').appendChild(tr);
}

function displayHeader()
{
    var thName = document.createElement('th');
    thName.textContent = "Name";
    var thDesc = document.createElement('th');
    thDesc.textContent = "Description"

    var tr = document.createElement('tr');
    tr.appendChild(thName);
    tr.appendChild(thDesc);

    document.querySelector('table').appendChild(tr);
}

function getAndDisplayList()
{
    clearError();
    clearNewEvent();
    clearList();

    fetch(urlBackend)
        .then(response =>
        {
            return response.json();
        })
        .then(data =>
        {
            displayHeader();
            for (let event of data.events)
            {
                displayEvent(event);
            }
        })
}

function setError(error)
{
    showElement('error')
    setElementContent('error', error);
}
