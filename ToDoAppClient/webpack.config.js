const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const webpack = require('webpack');

module.exports = env => {
    console.log('NODE_ENV: ', env.NODE_ENV);
    console.log('SERVER_URL: ', env.SERVER_URL);

    return {
        mode: env.NODE_ENV || 'development',
        entry: {
            app: './src/index.js'
        },
        devtool: env.NODE_ENV === 'production' ? 'source-map' : 'inline-source-map',
        devServer: {
            contentBase: './dist'
        },
        plugins: [
            new CleanWebpackPlugin(),
            new HtmlWebpackPlugin({
                template: "src/index.html"
            }),
            new webpack.DefinePlugin({
                SERVER_URL: JSON.stringify(env.SERVER_URL || "http://localhost:8090")
            })
        ],
        module: {
            rules: [
                {
                    test: /\.(js|jsx)$/,
                    exclude: /node_modules/,
                    use: ['react-hot-loader/webpack', 'babel-loader']
                },
                {
                    test: /\.css$/,
                    use: ['style-loader', {
                        loader: 'css-loader',
                        query: {
                            modules: true,
                            localIdentName: '[name]__[local]___[hash:base64:5]'
                        }
                    }]
                }
            ]
        },
        output: {
            filename: '[name].bundle.js',
            path: path.resolve(__dirname, 'dist')
        },
        optimization: {
            splitChunks: {
                chunks: 'all',
                minSize: 30000,
                maxSize: 0,
                minChunks: 1,
                maxAsyncRequests: 5,
                maxInitialRequests: 3,
                automaticNameDelimiter: '~',
                name: true,
                cacheGroups: {
                    vendors: {
                        test: /[\\/]node_modules[\\/]/,
                        priority: -10
                    },
                    default: {
                        minChunks: 2,
                        priority: -20,
                        reuseExistingChunk: true
                    }
                }
            }
        }
    };
};