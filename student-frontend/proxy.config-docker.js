const proxy = [
    {
      context: '/api',
      target: 'http://studentback-server:8080/student-backend/',
      pathRewrite: {'^/api' : ''}
    }
  ];
  
  module.exports = proxy;
  