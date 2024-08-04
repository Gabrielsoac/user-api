const btnNewUser = document.querySelector('.new-users');
const btnListUsers = document.querySelector('.list-users');
const btnGetUser = document.querySelector('.get-user');
const btnBackToMenu = document.querySelector('.backToMenu');

const spanNewUserSucess = document.querySelector('.newUserSucess');

const firstContainer = document.querySelector('.first-container');
const newUserContainer = document.querySelector('.new-user-container');
const listAllUsersContainer = document.querySelector('.list-all-users-container');
const getUserContainer = document.querySelector('.get-user-container');

let lastContainerOpen = firstContainer;

btnNewUser.onclick = () => {
    lastContainerOpen.classList.toggle('displayNone');
    newUserContainer.classList.toggle('displayNone');

    lastContainerOpen = newUserContainer
}

btnBackToMenu.onclick = () => {
    lastContainerOpen.classList.toggle('displayNone');
    firstContainer.classList.toggle('displayNone');

    lastContainerOpen = firstContainer
}

function newUser(event) {
    event.preventDefault();

    const formEL = new FormData(event.target);

    const username = formEL.get('username');
    const name = formEL.get('name');
    const apresentation = formEL.get('apresentation');
    const cep = formEL.get('cep');


    const body = {
        "username": username,
        "name": name,
        "apresentation": apresentation,
        "cep": cep
    }

    
    api('', 'POST', body)
    
}

function clearInputs() {
    const inputs = document.querySelectorAll('.inputsForm')
    for (let input of inputs) {
        input.value = ''
    }
}

async function api(path, method, body) {

    const url = 'https://user-api-production-6d74.up.railway.app/user' + path

    

    const request = {
        method: method,
        headers: {
            "Content-Type": "application/json" 
        },
        body: JSON.stringify(body)
    }


    try {
        const response = await fetch(url, request)
        const data = await response.json()

        clearInputs()
        spanNewUserSucess.classList.toggle('spanNewUserSucess')

    } catch (e) {
        console.log(e)
    }
}