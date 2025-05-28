async function login(username, password) {
    const response = await fetch('/api/getRefreshToken', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    })

    if(response.ok){
        const data = await response.json();
        sessionStorage.setItem('AccessToken',data.accessToken)
    }
}