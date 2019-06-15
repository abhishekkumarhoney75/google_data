import gmplot

longitude_list = [78.6474315, 78.6369814, 78.6284412, 78.6455218, 78.6354661, 12.855887]
latitude_list = [78.6474315, 78.6369814, 78.6284412, 78.6455218, 78.6354661, 12.855887]
gmap3 = gmplot.GoogleMapPlotter(17.4426475, 78.6465797, 13)

# scatter method of map object
# scatter points on the google map
gmap3.scatter(latitude_list, longitude_list, '# B22222', size=10, marker=False)

# Plot method Draw a line in
# between given coordinates
gmap3.plot(latitude_list, longitude_list, 'cornflowerblue', edge_width=5)

gmap3.draw("/Users/apple/Documents/study/google_data/Resources/map12.html")
