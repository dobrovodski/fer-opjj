$.ajax(
    {
        url: 'rest/images',
        dataType: 'json',
        success: function (data) {
            let tags = data;
            for (let i = 0; i < tags.length; i++) {
                let span = $('<span />').addClass('btn').click((e) => getThumbnails(tags[i])).html(tags[i]);
                $('#tag-list').append(span);
            }
        }
    }
)

function getThumbnails(tag) {
    $.ajax(
        {
            url: 'rest/images/thumbnail',
            data: {
                tag: tag,
                dummy: Math.random()
            },
            dataType: 'json',
            error: function (data) {
                console.log(data);
            },
            success: function (data) {
                let images = data;
                if (images.length == 0) {
                    let = 'No thumbnails found!';
                } else {
                    $("#thumbnails").empty();
                    let div = $("<div>");
                    for (let i = 0; i < images.length; i++) {
                        let img = $("<img>")
                            .attr('src', "data:image/png;base64," + images[i].base64)
                            .click((e) => getImage(images[i].id));

                        div.append(img);
                    }
                    div.appendTo('#thumbnails');
                }
            }
        }
    )
}

function getImage(imageName) {
    $.ajax(
        {
            url: 'rest/images/img',
            data: {
                id: imageName,
                dummy: Math.random()
            },
            dataType: 'json',
            error: function (data) {
                console.log(data);
            },
            success: function (data) {
                let image = data;

                $("#image").empty();
                $("<img>")
                    .attr('height', '600px')
                    .attr('src', "data:image/png;base64," + image.base64)
                    .appendTo('#image');
                $("<div>").html('<b>Description:</b> ' + image.description + '<br />' + '<b>Tags: </b>' + image.tags).appendTo('#image');
            }
        }
    )
}

