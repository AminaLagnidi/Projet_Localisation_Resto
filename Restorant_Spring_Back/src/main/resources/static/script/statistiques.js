$(document).ready(function () {
    // Fetch the count of different entities
    $.ajax({
        url: 'restos/count',
        type: 'GET',
        success: function (data) {
            $('#tresto').html(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    $.ajax({
        url: 'villes/restaurants',
        contentType: "application/json",
        dataType: "json",
        type: 'GET',
        success: function (data) {
            // Call a function to generate the restaurant by city chart with the retrieved data
            generateRestaurantByCityChart(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    $.ajax({
        url: 'villes/count',
        type: 'GET',
        success: function (data) {
            $('#tville').html(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    $.ajax({
        url: 'series/count',
        type: 'GET',
        success: function (data) {
            $('#tserie').html(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    $.ajax({
        url: 'specialites/count',
        type: 'GET',
        success: function (data) {
            $('#tspecialite').html(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    // Fetch the count of cities with historical data for chart
    $.ajax({
        url: 'villes/count',
        contentType: "application/json",
        dataType: "json",
        type: 'GET',
        success: function (data) {
            var labels = [];
            var dt = [];

            labels.push(data[0][0]);
            dt.push(data[0][1]);

            data.forEach((e, index) => {
                if (index == 0)
                    return;
                if (e[0] == labels[labels.length - 1] + 1) {
                    labels.push(e[0]);
                    dt.push(e[1]);
                } else {
                    do {
                        labels.push(labels[labels.length - 1] + 1);
                        dt.push(0);
                    } while (e[0] != labels[labels.length - 1] + 1);
                    labels.push(e[0]);
                    dt.push(e[1]);
                }
            });

            // Render the line chart for city counts
            renderLineChart(labels, dt, 'byYear', 'Evolution des achats', 'Année', 'Nombre des machines');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    // Fetch the count of different brands with historical data for chart
    $.ajax({
        url: 'marques/count',
        contentType: "application/json",
        dataType: "json",
        type: 'GET',
        success: function (data) {
            var labels = [];
            var dt = [];

            Object.keys(data).forEach(key => {
                labels.push(key);
                dt.push(data[key]);
            });

            // Render the bar chart for brand counts
            renderBarChart(labels, dt, 'myChart', 'Nombre des machines par marque', 'Marques', 'Nombre des machines');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    // Fetch data for the number of restaurants per zone
    $.ajax({
        url: 'zones/restaurants',
        contentType: "application/json",
        dataType: "json",
        type: 'GET',
        success: function (data) {
            // Call a function to generate the restaurant by zone chart with the retrieved data
            generateRestaurantByZoneChart(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

    // Function to render a line chart
    function renderLineChart(labels, data, chartId, chartTitle, xAxisLabel, yAxisLabel) {
        var ctx = document.getElementById(chartId).getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    data: data,
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                title: {
                    display: true,
                    text: chartTitle,
                    fontSize: 21,
                    padding: 20,
                    fontFamily: 'Calibri',
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        scaleLabel: {
                            display: true,
                            labelString: yAxisLabel
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: xAxisLabel
                        }
                    }],
                }
            }
        });
    }

    // Function to render a bar chart
    function renderBarChart(labels, data, chartId, chartTitle, xAxisLabel, yAxisLabel) {
        var ctx = document.getElementById(chartId).getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                title: {
                    display: true,
                    text: chartTitle,
                    fontSize: 21,
                    padding: 20,
                    fontFamily: 'Calibri',
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        scaleLabel: {
                            display: true,
                            labelString: yAxisLabel
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: xAxisLabel
                        }
                    }],
                }
            }
        });
    }

    // Function to generate the restaurant by city chart
    function generateRestaurantByCityChart(data) {
        var labels = [];
        var dt = [];

        // Process the data to get the labels and data
        // Adjust this part based on your actual data structure
        data.forEach(function (cityData) {
            labels.push(cityData.city);
            dt.push(cityData.restaurantCount);
        });

        // Use the data to generate the chart
        var ctx = document.getElementById('restaurantsByCityChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    data: dt,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Nombre de restaurants par ville',
                    fontSize: 21,
                    padding: 20,
                    fontFamily: 'Calibri',
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Nombre de restaurants'
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Villes'
                        }
                    }],
                }
            }
        });
    }

    // Function to generate the restaurant by zone chart
    function generateRestaurantByZoneChart(data) {
        var labels = [];
        var dt = [];

        // Process the data to get the labels and data
        // Adjust this part based on your actual data structure
        data.forEach(function (zoneData) {
            labels.push(zoneData.zone);
            dt.push(zoneData.restaurantCount);
        });

        // Use the data to generate the chart
        var ctx = document.getElementById('restaurantByZoneChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    data: dt,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Nombre de restaurants par zone',
                    fontSize: 21,
                    padding: 20,
                    fontFamily: 'Calibri',
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Nombre de restaurants'
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Zones'
                        }
                    }],
                }
            }
        });
    }
});
