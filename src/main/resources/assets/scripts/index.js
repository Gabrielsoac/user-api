// buttons
const btnNewUser = document.querySelector('.new-users');
const btnListUsers = document.querySelector('.list-users');
const btnGetUserContainer = document.querySelector('.get-user');
const btnGetUser = document.querySelector('.btnGetUser')
const btnsBackToMenu = document.querySelectorAll('.backToMenu');
const btnNewSubmit = document.querySelector('.new-submit')


// alertas
const spanNewUserSucess = document.querySelector('.newUserSucess');
const spanNewUserError = document.querySelector('.newUserError');


// container
const firstContainer = document.querySelector('.first-container');
const newUserContainer = document.querySelector('.new-user-container');
const listAllUsersContainer = document.querySelector('.list-all-users-container');
const getUserContainer = document.querySelector('.get-user-container');
const listUserContainer = document.querySelector('.list-user-container')
const getUserView = document.querySelector('.get-user-view')

const spinner = document.querySelector('.spinner')

let lastContainerOpen = firstContainer;


const head = `
            <div>
                <span class="bold">Username</strong></span>
                <span class="bold">Nome</span>
                <span class="bold">Cidade</span>
                <span class="bold">Estado</span>
            </div>
                `
                
const headUser = `
            <div>
                <span class="get-user-viewApresentation bold">Username</strong></span>
                <span class="get-user-viewApresentation bold">Nome</span>
                <span class="get-user-viewApresentation bold">Descrição</span>
                <span class="get-user-viewApresentation bold">Cidade</span>
                <span class="get-user-viewApresentation bold">Estado</span>
            </div>
                `


//on-clicks
btnNewUser.onclick = () => {
    lastContainerOpen.classList.toggle('displayNone');
    newUserContainer.classList.toggle('displayNone');

    lastContainerOpen = newUserContainer

    spanNewUserSucess.classList.add('displayNone')
    spanNewUserError.classList.add('displayNone')
}

btnListUsers.onclick = () => {
    lastContainerOpen.classList.toggle('displayNone');
    listAllUsersContainer.classList.toggle('displayNone');

    lastContainerOpen = listAllUsersContainer

    listAllUser()
}

btnGetUserContainer.onclick = () => {
    lastContainerOpen.classList.toggle('displayNone');
    getUserContainer.classList.toggle('displayNone');

    lastContainerOpen = getUserContainer

}


btnGetUser.onclick = () => {
    getUser()
}

for (let btn of btnsBackToMenu) {
    btn.onclick = () => {
        clearInputs()
        backToMenu()
        getUserView.classList.add('displayNone')
        getUserView.innerHTML = '<i style="position: relative; bottom: 5px;" class="fa-solid fa-spinner fa-spin-pulse fa-2xl spinner"></i>'

        
    }
}


// functions

function backToMenu() {
    lastContainerOpen.classList.toggle('displayNone');
    firstContainer.classList.toggle('displayNone');
    
    lastContainerOpen = firstContainer
    
}



async function newUser(event) {
    event.preventDefault();
    spanNewUserSucess.classList.add('displayNone')
    spanNewUserError.classList.add('displayNone')


    btnNewSubmit.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>'

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

    
    const data = await api('', 'POST', body)


    if (data.status) {

        if (data.status !== 201) {
            btnNewSubmit.innerHTML = 'Cadastrar'
            spanNewUserError.classList.toggle('displayNone')
            return
        }

    }

    clearInputs()
    spanNewUserSucess.classList.toggle('displayNone')
    btnNewSubmit.innerHTML = 'Cadastrar'

    
}

async function listAllUser() {

    const data = await api('', 'GET')
    

    if (data) {
        listUserContainer.removeChild(spinner)
        createTable(data.users, listUserContainer)
    }

}

async function getUser() {

    getUserView.classList.toggle('displayNone')
    
    const username = document.querySelector('.username').value
    const path = '/' + username

    
    try {
        const data = await api(path, 'GET')

        if (data.status) {
            getUserView.innerHTML = 'Usuário não encontrato'
            return
        }

        if (data) {
            createline(data, getUserView)
            return
        }

    } catch (e) {
        
    }

}

function createline(data, container) {

    container.innerHTML = headUser
    
    const div1 = document.createElement('div')

    for (let key in data) {
        const span = document.createElement('span')

        span.classList.add('get-user-view-span')

        span.innerHTML = data[key]

        div1.appendChild(span)
    }

    container.appendChild(div1)
}

function createTable(data, container) {

    container.innerHTML = head

    for (let index in data) {
        const div1 = document.createElement('div')
        const div2 = document.createElement('div')

        for (let key in data[index]) {

            if (key === "apresentation") continue

            div2.classList.add('line-user')
            const span = document.createElement('span')
            span.innerHTML = data[index][key]

            div2.appendChild(span)
        }

        
        container.appendChild(div2)
    }

}



function clearInputs() {
    const inputs = document.querySelectorAll('.inputsForm')
    for (let input of inputs) {
        input.value = ''
    }
}

async function api(path, method, body) {

    const url = 'https://user-api-production-6d74.up.railway.app/user' + path

    let request = {};

    if (body) {
        request = {
            method: method,
            headers: {
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(body)
        }
    
    }

    try {
        const response = await fetch(url, request)
        const data = await response.json()
        
        return data

    } catch (e) {
        console.log(e)

    }
}