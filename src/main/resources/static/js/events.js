let urlBackend = "http://localhost:8080/events";

function addEvent()
{
    let payload = {
        name: getInputField('name'),
        description: getInputField('desc'),
        timezone: getInputField('timezone'),
        start: getInputField('start')
    }

    let options = {
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
    clearInputField('name');
    clearInputField('desc');
    clearInputField('timezone');
    clearInputField('start');
}

function displayEvent(event)
{
    let tr = document.createElement('tr');
    tr.appendChild(newElement('td', 'names', event.name));
    tr.appendChild(newElement('td', 'descs', event.description));
    tr.appendChild(newElement('td', 'starts', event.start));
    tr.appendChild(newElement('td', 'ends', event.end));

    document.querySelector('table').appendChild(tr);
}

function displayHeader()
{
    let tr = document.createElement('tr');
    tr.appendChild(newElement('th', 'header', 'Name'));
    tr.appendChild(newElement('th', 'header', 'Description'));
    tr.appendChild(newElement('th', 'header', 'Start'));
    tr.appendChild(newElement('th', 'header', 'End'));

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
