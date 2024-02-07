const { VueLoaderPlugin } = require('vue-loader');

module.exports = {
  // ...other webpack configuration settings...

  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.css$/, // Add a rule for processing CSS files
        use: ['style-loader', 'css-loader']
      }
      // Other rules for JavaScript, CSS, etc.
    ]
  },

  plugins: [
    // Add the VueLoaderPlugin to your plugins array
    new VueLoaderPlugin()
  ]
};

