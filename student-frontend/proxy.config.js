const proxy = [
  {
    context: '/api',
    target: 'http://localhost:8080/student-backend/',
    pathRewrite: {'^/api' : ''}
  }
];

module.exports = proxy;
