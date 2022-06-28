//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The release.ts is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

import semanticRelease from 'semantic-release';
import fs from 'fs';

semanticRelease({
    // 选项
    branches: [
        'master',
    ],
    repositoryUrl: 'https://github.com/moe-org/utopia.git',
    dryRun: true,
    ci: true,
    plugins: ['@semantic-release/commit-analyzer', '@semantic-release/release-notes-generator'],
    tagFormat: "${version}"
}, {})
    .then((result) => {
        if (result) {
            // 发布
            const {lastRelease, commits, nextRelease, releases} = result;

            // 把新版本的信息写入文件
            fs.writeFileSync("./version.txt", nextRelease.version, {encoding: "utf-8"});
            fs.writeFileSync("./change-log.txt", nextRelease.notes, {encoding: "utf-8"});
        } else {
            console.log('No release published.');
        }
    })
    .catch((err) => {
        console.error('The automated release failed with %O', err)
    });


