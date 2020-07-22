const proxy = [
    {
      context: '/api',
      target: 'http://backend_server:8080/student-backend/',
      pathRewrite: {'^/api' : ''}
    }
  ];
  
  module.exports = proxy;
  