var margin = {top: 20, right: 120, bottom: 20, left: 120};
var width = 960 - margin.right - margin.left;
var height = 1200 - margin.top - margin.bottom;
	
var i = 0,
	duration = 750, // controls how long nodes take to spring out
	root;

var tree = d3.layout.tree()
	.size([width, height]);

var diagonal = d3.svg.diagonal();
	// .projection(function(d) { return [d.x, d.y]; });

var svg = d3.select("body").append("svg")
	.attr("width", width + margin.right + margin.left)
	.attr("height", height + margin.top + margin.bottom)
	.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

// d3.json("/d/4063550/flare.json", 
// function (error, flare) {
root = json;//flare;
root.x0 = width / 2;
root.y0 = 0;

function collapse(d) {
	if (d.children) {
		d._children = d.children;
		d._children.forEach(collapse);
		d.children = null;
	}
}

function expand(d) {
	if (d._children) {
		d.children = d._children;
		d.children.forEach(expand);
		d._children = null;
	}
}

function getMaxDepth (tree) {
	if (tree.children) {
		return 1 + tree.children.map(getMaxDepth).reduce(function(c,t) {return c>t?c:t}, 0);
	}
	else {
		return 0;
	}
}

// splits a sorted list into sublists containing all of the same element
// f can be used to transform elements for comparison
function split_sublists (lst, f) {
	var result = [];
	var temp = [];
	var current = lst[0];
    
	for (var i=0; i<lst.length; i++) {
		if (f(current) === f(lst[i])) {
			temp.push(lst[i]);
		}
		else {
			result.push(temp);
            current = lst[i];
			temp = [current];
		}
	}
    result.push(temp);
	return result;
}

// does not collect leaves
function collectNodes (root) {
	var result = [];
	var q = [root];

	while (q.length > 0) {
		var current = q.shift();
		if (current.children) {
			result.push(current);
			current.children.forEach(function(x) {q.push(x);});
		}
	}

	return result;
}

function collapseAll(d) {

	/**
	* Collapsing nodes one by one
	*/

	var nodes = collectNodes(d);
	nodes.reverse();

	nodes.forEach(function (node, i) {

		setTimeout(function() {

			if (node.children) {
				// hide the children of this node
				node._children = node.children;
				node.children = null;

				// update this node
				update(node);
			}

		}, i*duration);
	});

	/**
	* Imperative, unsuccessful attempt to expand nodes at the same level together
	*/

	// var nodes = collectNodes(d);
	// nodes.reverse(); // nodes in decreasing depth, no leaves

	// var lists = split_sublists(nodes, function(d) {return d.depth;});

	// lists.forEach(function(lst, i) {
	// 	lst.forEach(function(node, j) {
	// 		setTimeout(function() {

	// 		console.log("node " + node.name);
	// 			if (node.children) {
	// 				// hide the children of this node
	// 				node._children = node.children;
	// 				node.children = null;

	// 				// update this node
	// 				update(node);
	// 			}

	// 		}, i*duration);
	// 	});
	// });

	/**
	* Recursive version of the above
	*/

	// var maxDepth = getMaxDepth(d);
	// console.log("depth: " +maxDepth);

	// function helper (d) {
	// 	if (d.children) {
	// 		d.children.forEach(function (child) {
	// 			helper(child);
	// 		});
	// 		setTimeout(function() {
	// 			// hide the children of this node
	// 			d._children = d.children;
	// 			d.children = null;

	// 			// update this node
	// 			update(d);
	// 		}, (maxDepth-d.depth)*750 + Math.random()*100);
	// 	}
	// }

	// helper(d);
}

function expandAll(d) {
	if (d._children) { // has hidden children
		// show children first
		d.children = d._children;
		d._children = null;
		update(d);

		// recurse only after waiting
		setTimeout(function() {
			d.children.forEach(expandAll);
		}, duration);
	}
	else if (d.children) { // all children are shown
		// immediately recurse
		d.children.forEach(expandAll);
	}
}

root.children.forEach(collapse);
update(root);
// }(null, json);
// );

d3.select(self.frameElement).style("height", "800px");

// Toggle children on click.
function click(d) {
	if (d.children) { // if children are shown
		d._children = d.children;
		d.children = null;
	} else { // if children are hidden
		d.children = d._children;
		d._children = null;
	}
	update(d);
}

// Updates all children of a given node.
function update(source) {

	// Compute the new tree layout.
	var nodes = tree.nodes(root).reverse(),
		links = tree.links(nodes);

	// Normalize for fixed-depth.
	nodes.forEach(function(d) { d.y = d.depth * 100; });

	// Update the nodes...
	var node = svg.selectAll("g.node")
		.data(nodes, function(d) { return d.id || (d.id = ++i); });

	// Enter any new nodes at the parent's previous position.
	var nodeEnter = node.enter().append("g")
		.attr("class", "node")
		.attr("transform", function(d) { return "translate(" + source.x0 + "," + source.y0 + ")"; })
		.on("click", click);

	nodeEnter.append("circle")
		.attr("r", 1e-6)
		.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

	nodeEnter.append("text")
		.attr("y", function(d) { return d.children || d._children ? -15 : 15; })
		.attr("dy", ".35em")
		.attr("text-anchor", function(d) { return "middle";})
		// .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
		.text(function(d) { return d.name; })
		.style("fill-opacity", 1e-6);

	// Transition nodes to their new position.
	var nodeUpdate = node.transition()
		.duration(duration)
		.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

	nodeUpdate.select("circle")
		.attr("r", 4.5)
		.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

	nodeUpdate.select("text")
		.style("fill-opacity", 1);

	// Transition exiting nodes to the parent's new position.
	var nodeExit = node.exit().transition()
		.duration(duration)
		.attr("transform", function(d) { return "translate(" + source.x + "," + source.y + ")"; })
		.remove();

	nodeExit.select("circle")
		.attr("r", 1e-6);

	nodeExit.select("text")
		.style("fill-opacity", 1e-6);

	// Update the links…
	var link = svg.selectAll("path.link")
		.data(links, function(d) { return d.target.id; });

	// Enter any new links at the parent's previous position.
	link.enter().insert("path", "g")
		.attr("class", "link")
		.attr("d", function(d) {
			var o = {x: source.x0, y: source.y0};
			return diagonal({source: o, target: o});
		});

	// Transition links to their new position.
	link.transition()
		.duration(duration)
		.attr("d", diagonal);

	// Transition exiting nodes to the parent's new position.
	link.exit().transition()
		.duration(duration)
		.attr("d", function(d) {
			var o = {x: source.x, y: source.y};
			return diagonal({source: o, target: o});
		})
		.remove();

	// Stash the old positions for transition.
	nodes.forEach(function(d) {
		d.x0 = d.x;
		d.y0 = d.y;
	});
}