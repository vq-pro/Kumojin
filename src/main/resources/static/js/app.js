let urlBackend = "http://localhost:8080/events";

function addEvent()
{
    const payload = {
        name: getInputField('name'),
        description: getInputField('desc')
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
                getAndDisplayEventList();

            } else if (response.status === 400)
            {
                setError('Invalid event');

            } else if (response.status === 409)
            {
                setError('Duplicate event');

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
    var tdStarts = document.createElement('td');
    tdStarts.id = "starts";
    tdStarts.textContent = event.start;

    var tr = document.createElement('tr');
    tr.appendChild(tdName);
    tr.appendChild(tdDesc);
    tr.appendChild(tdStarts);

    document.querySelector('table').appendChild(tr);
}

// FIXME-1 Define newElement() method
function displayHeader()
{
    var thName = document.createElement('th');
    thName.id = "header"
    thName.textContent = "Name";
    var thDesc = document.createElement('th');
    thDesc.id = "header"
    thDesc.textContent = "Description"
    var thStart = document.createElement('th');
    thStart.id = "header"
    thStart.textContent = "Start"

    var tr = document.createElement('tr');
    tr.appendChild(thName);
    tr.appendChild(thDesc);
    tr.appendChild(thStart);

    document.querySelector('table').appendChild(tr);
}

function getAndDisplayEventList()
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
