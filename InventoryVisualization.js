var SMARTPORTABLE = {
    
     /*return google visualization data*/
      getvisualizationData: function (jsonData) {
    
        var var1,var2, dataArray = [],
    
        data = new google.visualization.DataTable();
    
        data.addColumn('string', 'Name');
        data.addColumn('number', 'Product Count');
        data.addColumn({
          type: 'string',
          role: 'tooltip',
          'p': {
          'html': true
          }
        });
        
    
        /* for loop code for changing inputdata to 'data' of type google.visualization.DataTable*/
        $.each(jsonData, function (i, obj) {

            var1 = "Count : " + obj.productCount + "";
            var2 = "Name : " + obj.productName + "";
            dataArray.push([obj.productName, obj.productCount, SMARTPORTABLE.returnTooltip(var1,var2)]);
        });
    
        data.addRows(dataArray);
    
        return data;
      },
      /*return options for bar chart: these options are for various configuration of chart*/
      getOptionForBarchart: function () {
    
        var options = {
    
        chart: {
          title: 'ABC',
          subtitle: '',
        },
        animation: {
          duration: 2000,
          easing: 'out'
        },
        hAxis: {
          baselineColor: '#ccc'
        },
        vAxis: {
          baselineColor: '#ccc',
          gridlineColor: '#fff'
        },
        isStacked: true,
        height: 1500,
        backgroundColor: '#fff',
        colors: ["#68130E", "#c65533"],
        fontName: 'roboto',
        fontSize: 12,
        legend: {
          position: 'top',
          alignment: 'end',
          textStyle: {
          color: '#b3b8bc',
          fontName: 'roboto',
          fontSize: 12
          }
        },
        tooltip: {
          isHtml: true,
          showColorCode: true,
          isStacked: true
        }
        };
        return options;
      },
      /*Draws a Bar chart*/
      drawBarChart: function (inputdata) {
    
        var barOptions = SMARTPORTABLE.getOptionForBarchart(),
        data = SMARTPORTABLE.getvisualizationData(inputdata),
        chart = new google.visualization.BarChart(document.getElementById('inventory_chart'));
        chart.draw(data, barOptions);
        /*for redrawing the bar chart on window resize*/
        $(window).resize(function () {
        chart.draw(data, barOptions);
        });
      },
      /* Returns a custom HTML tooltip for Visualization chart*/
      returnTooltip: function (dataVar1, dataVar2) {
    
        return "<div style='height:30px;width:300px;font:12px,roboto;padding:5px 5px 5px 5px;border-radius:3px;'>" +
        "<span style='color:#68130E;font:12px,roboto;padding-right:20px;'>" + dataVar1 + "</span>" +
        "<span style='color:#c65533;font:12px,roboto;'>" + dataVar2 + "</span></div>";
      },
      /*Makes ajax call to servlet and download data */
      getData: function () {
    
          $.ajax({
            url: "InventoryVisualization",
            dataType: "JSON",
            success: function (data) {
              SMARTPORTABLE.drawBarChart(data);
            }
          });
      }
      };
    
    google.load("visualization", "1", { packages: ["corechart"] });
    
    $(document).ready(function () { SMARTPORTABLE.getData(); });