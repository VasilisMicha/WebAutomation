const axios = require('axios').default;
const { wrapper } = require('axios-cookiejar-support');
const { CookieJar } = require('tough-cookie');

// 302 error is redirect
const noRedirectOptions = {
    maxRedirects: 0,
    validateStatus: status => status >= 200 && status < 303
};

async function main() {
    const cookieJar = new CookieJar();
    const session = wrapper(axios.create({ jar: cookieJar }));

    session.defaults.withCredentials = true;
    session.defaults.headers['User-Agent'] = 'Chrome/126.0.6478.127';

    const server = 'gr30';
    const res = await session.get(`https://${server}.herozerogame.com/`);

    const loginCheckUrl = `https://${server}.herozerogame.com/request.php`;
    const email = encodeURIComponent("bmichaelides859@gmail.com");
    const password = encodeURIComponent("987654321");
    const data = `email=${email}&password=${password}&platform=&platform_user_id=&client_id=${server}1711561368&app_version=228&device_info=%7B%22language%22%3A%22xu%22%2C%22pixelAspectRatio%22%3A1%2C%22screenDPI%22%3A72%2C%22screenResolutionX%22%3A1536%2C%22screenResolutionY%22%3A864%2C%22touchscreenType%22%3A0%2C%22os%22%3A%22HTML5%22%2C%22version%22%3A%22WEB%208%2C9%2C7%2C0%22%7D&device_id=web&action=loginUser&user_id=0&user_session_id=0&client_version=html5_228&auth=b67c0311ba923f64edc58bf3d7957b60&rct=1&keep_active=true&device_type=web`;

    const check = await session.post(loginCheckUrl, data, noRedirectOptions);
    console.log(check.data);
}

main();
